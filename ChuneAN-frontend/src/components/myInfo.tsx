import {useState,useEffect,useRef} from 'react'
import axios from "axios";
import type {Following, User} from './Interfaces.tsx';
import {useNavigate,Link,Outlet} from "react-router-dom";
import type {Post} from "./Interfaces.tsx";
import {FaRegHeart} from "react-icons/fa6";
import {FaCommentDots} from "react-icons/fa";
import { MdKeyboardArrowRight } from "react-icons/md";
import { MdKeyboardArrowDown } from "react-icons/md";

export default function MyInfo(){
    const [user,setUser] = useState<User | null>(null);
    const [post,setPost] = useState<Post[]>([]);
    const [showFollowing, setShowFollowing] = useState(false);
    const [showFollower, setShowFollower] = useState(false);
    const [followingList, setFollowingList] = useState<Following[]>([]);
    const audioRefs = useRef<HTMLAudioElement[]>([]);
    const navigate = useNavigate();
    useEffect(()=>{
        const getUser = async() =>{
            try {
                const res = await axios.get("http://localhost:8080/api/users/info", {withCredentials: true});
                setUser(res.data);
            }
            catch{
                alert("Can't get user's information");
                navigate("/home");
            }
        }
        getUser().then();
    },[]);

    useEffect(() => {
        if(!user) return;
        const getPost = async() =>{
            try{
                const resPost= await axios.get(`http://localhost:8080/api/users/posts/own/${user?.id}`,{withCredentials: true});
                setPost(resPost.data);
            }
            catch{
                alert("Can't get user's posts");
                navigate("/home");
            }
        }
        getPost().then();
    }, [user]);

    useEffect(() => {
        const stopAudio = () =>{
            audioRefs.current.forEach((audio) =>{
                if(audio && !audio.paused){
                    audio.pause();
                }
            })
        }
        stopAudio();
    }, []);

    useEffect(()=>{
        const getFollowingList = async() =>{
            try{
                const res = await axios.get("http://localhost:8080/api/users/following", {withCredentials: true});
                setFollowingList(res.data);
            }
            catch(e){
                alert("Can't get user's following");
            }
        }
        getFollowingList().then();
    },[]);

    const handleFollow = async (followingId:string, username:string, avatarUrl:string) =>{
        const res = await axios.post(`http://localhost:8080/api/users/follow`,{followingId: followingId},{withCredentials: true});
        setFollowingList(prev => [...prev,{userId:followingId,username:username,avatarUrl:avatarUrl}]);
        alert(res.data);
    }

    const handleUnfollow = async (followingId:string) =>{
        const res = await axios.delete(`http://localhost:8080/api/users/unfollow`,{data:{ followingId },withCredentials: true});
        setFollowingList(followingList.filter(f => f.userId !== followingId));
        alert(res.data);
    }

    return (
        <div className="grid grid-cols-3 bg-black min-h-screen text-white p-4 ">
            <div className="flex flex-col items-center gap-[1rem] relative bg-[#0a0b0d] p-4 rounded-[1rem]">
                <img alt="user's avatar"
                     src={user?.avatarUrl ? user.avatarUrl : "default_avatar.jpg"}
                     className="rounded-full w-[7rem] h-[7rem] border-2 border-white"
                />
                <Link to="edit" className="absolute p-2 px-[2rem] rounded-[100vw] right-0 top-0 bg-white text-black hover:bg-gray-400">Edit</Link>
                <p className="text-3xl font-bold">{user?.username}</p>
                <div className="flex gap-[2rem] mx-auto text-[#b9babd]">
                    <p>{user?.followingList.length} following</p>
                    <p>{user?.followerList.length} follower</p>
                </div>
                {user?.bio && <p>{user.bio}</p>}
                <p>Email: {user?.email}</p>
            </div>
            <div>
                <div className="flex flex-col gap-[2rem] w-full p-[1rem]">
                    {post.length > 0 ? (post.map((post:Post,index:number)=>(
                            <div key={index} className="p-[1rem] bg-[#1E1E1E] w-full rounded-[1rem] gap-[1rem] flex flex-col border-[1px] border-[#2A2A2A]">
                                <div className="flex items-center gap-[1rem]">
                                    <img src={post?.avatarUrl ? post.avatarUrl : "/default_avatar.jpg"} className="rounded-full w-[3rem] h-[3rem]" alt="user's avatar"/>
                                    <div className="flex flex-col gap-1">
                                        <p className="text-xl font-medium">{post.username}</p>
                                        <p className="text-[#b9babd] text-[0.875rem]">{post.createdAt ? new Date(post.createdAt).toLocaleString() : "No date"}</p>
                                    </div>
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
                    ) : ("User currently has no post")}
                </div>
            </div>
            <div className="bg-[#0a0b0d] rounded-[1rem] p-4">
                <div className="flex flex-col gap-[1rem]">
                    <div onClick={()=>setShowFollowing(!showFollowing)} className="select-none text-3xl font-medium cursor-pointer hover:bg-[#1d1e23] w-full p-2 px-4 rounded-md flex items-center">
                        <p>Following</p>
                        {showFollowing ? <MdKeyboardArrowDown className="ml-auto"/> : <MdKeyboardArrowRight className="ml-auto"/>}
                    </div>
                    <div className="flex flex-col gap-[1rem] overflow-y-auto max-h-[10rem]">
                        {showFollowing && (user?.followingList && user?.followingList.length > 0 ? (
                            user?.followingList.map((following,index)=>(
                                <div key={index} className="flex items-center gap-[1rem] w-full bg-[#1d1e23] p-3 px-5 rounded-[1rem]">
                                    <img className="w-[2rem] h-[2rem] rounded-full" src={following?.avatarUrl ? following.avatarUrl : "/default_avatar.jpg" } alt="following's avatar" />
                                    <a href="http://localhost:5173/otherInfo" className="text-xl font-medium hover:underline">{following.username}</a>
                                    {!followingList.some(f=>f.userId === following.userId)
                                        ?<button
                                            onClick={()=>handleFollow(following.userId,following.username,following.avatarUrl)}
                                            className="bg-white text-black font-medium p-2 px-4 rounded-[100vw] cursor-pointer hover:bg-gray-500 ml-auto">
                                            Follow
                                        </button>
                                        :<button
                                            onClick={()=>handleUnfollow(following.userId)}
                                            className="bg-red-500 text-white font-medium p-2 px-4 rounded-[100vw] cursor-pointer hover:bg-red-700 ml-auto">
                                            Unfollow
                                        </button>}
                                </div>
                            ))
                        ) : (<div className="flex items-center gap-[1rem] w-full bg-[#1d1e23] p-3 px-5 rounded-[1rem]">You follow no one :(</div>))}
                    </div>
                </div>
                <div className="flex flex-col gap-[1rem] mt-[1rem]">
                    <div onClick={()=>setShowFollower(!showFollower)} className="select-none text-3xl font-medium cursor-pointer hover:bg-[#1d1e23] w-full p-2 px-4 rounded-md flex items-center">
                        <p>Follower</p>
                        {showFollower ? <MdKeyboardArrowDown className="ml-auto"/> : <MdKeyboardArrowRight className="ml-auto"/>}
                    </div>
                    <div className="flex flex-col gap-[1rem] overflow-y-auto max-h-[10rem]">
                        {showFollower && (user?.followerList && user?.followerList.length > 0 ? (
                            user?.followerList.map((follower,index)=>(
                                <div key={index} className="flex items-center gap-[1rem] w-full bg-[#1d1e23] p-3 px-5 rounded-[1rem]">
                                    <img className="w-[2rem] h-[2rem] rounded-full" src={follower?.avatarUrl ? follower.avatarUrl : "/default_avatar.jpg" } alt="following's avatar" />
                                    <a href="http://localhost:5173/otherInfo" className="text-xl font-medium cursor-pointer hover:underline">{follower.username}</a>
                                    {!followingList.some(f=>f.userId === follower.userId)
                                    ?<button
                                            onClick={()=>handleFollow(follower.userId,follower.username,follower.avatarUrl)}
                                            className="bg-white text-black font-medium p-2 px-4 rounded-[100vw] cursor-pointer hover:bg-gray-500 ml-auto">
                                            Follow
                                    </button>
                                    :<button
                                            onClick={()=>handleUnfollow(follower.userId)}
                                            className="bg-red-500 text-white font-medium p-2 px-4 rounded-[100vw] cursor-pointer hover:bg-red-700 ml-auto">
                                            Unfollow
                                    </button>}
                                </div>
                            ))
                        ): <div className="flex items-center gap-[1rem] w-full bg-[#1d1e23] p-3 px-5 rounded-[1rem]">No one follow you :(</div>)}
                    </div>
                </div>
            </div>
            <Outlet/>
        </div>
    )
}