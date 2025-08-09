import {Link,Outlet, useLocation,useNavigate} from 'react-router-dom';
import { LuNotebookPen } from "react-icons/lu";
import {useState,useEffect,useRef} from "react";
import { FaRegHeart } from "react-icons/fa6";
import { FaCommentDots } from "react-icons/fa";
import {useUser} from "./userContext.tsx";
import type {Post, Following} from "./Interfaces.tsx";
import axios from "axios";
export default function Feed(){
    const[data,setData] = useState<Post[]>([]);
    const [followingList,setFollowingList] = useState<Following[]>([]);
    const [additionalPath,setAdditionalPath] = useState<string>("/newest");
    const audioRefs = useRef<HTMLAudioElement[]>([]);
    const location = useLocation();
    const navigate = useNavigate();
    const user =useUser();
    useEffect(() => {
        const getData = async() =>{
            const res = await axios.get(`http://localhost:8080/api/users/posts${additionalPath}`, {withCredentials:true});
            setData(res.data)
        }
        const stopAudio = () =>{
            audioRefs.current.forEach((audio) =>{
                if(audio && !audio.paused){
                    audio.pause();
                }
            })
        }
        getData().then();
        stopAudio();
    }, [additionalPath,location]);

    useEffect(()=>{
        const getFollowing = async() =>{
            try{
                const res = await axios.get("http://localhost:8080/api/users/following",{withCredentials:true});
                setFollowingList(res.data);
            }
            catch(e){
                alert("Error!")
            }
        }
        getFollowing().then();
    },[])

    const handleFollow = async (followingId:string, username:string, avatarUrl:string) =>{
        try{
            const res = await axios.post("http://localhost:8080/api/users/follow",{followingId:followingId},{withCredentials:true});
            setFollowingList(prev => [...prev,{userId:followingId, username, avatarUrl}]);
            alert(res.data);
        }
        catch (e){
            alert("Error! Please try again later.");
        }
    }

    const handleUnfollow = async (followingId:string) =>{
        try{
            const res = await axios.delete("http://localhost:8080/api/users/unfollow",{data: { followingId },withCredentials:true});
            setFollowingList(prev=>prev.filter(user => user.userId !== followingId));
            alert(res.data);
        }
        catch (e){
            alert("Error! Please try again later.");
        }
    }
    console.log(followingList);
    return (
        <main className="flex gap-4 p-4 justify-between">
            <div className="flex flex-col gap-[1rem] bg-[#0a0b0d] p-[1rem] px-[2rem] min-w-[18rem] w-[20%] min-h-screen rounded-[7px]">
                <div className="flex flex-col gap-[1rem]">
                    <p onClick={()=>setAdditionalPath("/newest")} className="text-xl font-semibold cursor-pointer hover:bg-[#292b33] p-4 px-[1.5rem] rounded-[1rem]">Newest</p>
                </div>
                <div className="flex flex-col gap-[1rem]">
                    <p onClick={()=>setAdditionalPath("/trending")} className="text-xl font-semibold cursor-pointer hover:bg-[#292b33] p-4 px-[1.5rem] rounded-[1rem]">Trending</p>
                </div>
                <div className="flex flex-col gap-[1rem]">
                    <p onClick={()=>setAdditionalPath("/following")} className="text-xl font-semibold cursor-pointer hover:bg-[#292b33] p-4 px-[1.5rem] rounded-[1rem]">Following</p>
                </div>
                <Link to="create" className="flex mt-[2rem] justify-center items-center bg-red-700 hover:bg-red-900 text-xl p-4 p-x-[1rem] rounded-[100vw] font-medium gap-2">
                    <LuNotebookPen className="text-xl"/>
                    <p>What's new?</p>
                </Link>
                <Outlet/>
            </div>
            <div className="flex flex-col gap-[2rem] w-full p-[1rem]">
                {data.length > 0 ? (data.map((post:Post,index:number)=>(
                    <div key={index} className="p-[1rem] bg-[#1E1E1E] w-full rounded-[1rem] gap-[1rem] flex flex-col border-[1px] border-[#2A2A2A]">
                        <div className="flex items-center gap-[1rem]">
                            <img src={post?.avatarUrl ? post.avatarUrl : "/default_avatar.jpg"} className="rounded-full w-[3rem] h-[3rem]" alt="user's avatar"/>
                            <div className="flex flex-col gap-1">
                                <p onClick={()=>navigate(`/otherInfo/${post.userId}`)} className="text-xl font-medium cursor-pointer hover:underline">{post.username}</p>
                                <p className="text-[#b9babd] text-[0.875rem]">{post.createdAt ? new Date(post.createdAt).toLocaleString() : "No date"}</p>
                            </div>
                            {(post.userId != user?.id) && (!followingList.some(f=>f.userId === post.userId) ? <button className="bg-white text-black p-2 px-[2rem] rounded-[100vw] font-medium text-[0.875rem] cursor-pointer ml-auto" onClick={()=>handleFollow(post.userId,post.username,post.avatarUrl)}>follow</button>
                                : <button className="ml-auto cursor-pointer p-2 px-[2rem] rounded-[100vw] font-medium text-[0.875rem] bg-red-700 text-white" onClick={()=>handleUnfollow(post.userId)}>unfollow</button>)
                            }
                        </div>
                        <div className="border-b-[2px] border-[#2A2A2A]"></div>
                        <div className="flex flex-col gap-2">
                            <p className="text-xl font-medium">{post.title}</p>
                            <p>{post.content}</p>
                        </div>
                        <div className="ml-[2rem]">
                            {post.trackUrl == ""
                                ? <p className="rounded-[100vw] bg-white text-black w-max px-[2rem] p-3 font-medium">There is no track available</p>
                                : <audio controls ref={(el)=>{
                                        if(el) {
                                            audioRefs.current[index] = el;
                                        }
                                }}
                                         onPlay={()=>{
                                             audioRefs.current.forEach((audio,i)=>{
                                                 if(i!== index && audio && !audio.paused){
                                                     audio.pause();
                                                     audio.currentTime = 0;
                                                 }
                                             })
                                         }} src={"http://localhost:8080/" + encodeURI(post.trackUrl)}/>}
                        </div>
                        <div className="flex items-center gap-[2rem] text-xl">
                            <div className="flex items-center gap-2">
                                <FaRegHeart/>
                                <p>{post.likeCount}</p>
                            </div>
                            <div className="flex items-center gap-2">
                                <FaCommentDots/>
                                <p>{post.commentCount}</p>
                            </div>
                        </div>
                    </div>
                ))
                ) : ("No posts were found")}
            </div>
            <div className="flex flex-col gap-[2rem] min-w-[18rem] bg-[#0a0b0d] p-[1rem] px-[2rem] w-[20%] min-h-screen rounded-[7px]">
                <div>
                    <p className="text-xl font-semibold text-center">Recommended to follow</p>
                </div>
                <div>
                    <p className="text-xl font-semibold text-center">Recommended Bands</p>
                </div>
            </div>
            </main>
    )
}