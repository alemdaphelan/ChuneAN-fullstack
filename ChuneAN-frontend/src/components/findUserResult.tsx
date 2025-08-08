import {useState, useEffect} from "react";
import axios from "axios";
import type {User} from "./Interfaces.tsx";
import {useNavigate} from "react-router-dom";
import {searchUser, useUser} from "./userContext.tsx";

export default function UserResult(){
    const [data,setData] = useState<User[]>([]);
    const navigate = useNavigate();
    const user = useUser();
    const search = searchUser();
    const [loading,setLoading] = useState<boolean>(true);
    useEffect(() => {
        const getData = async() =>{
            try{
                const res = await axios.post("http://localhost:8080/api/users/find",{"search":search},{withCredentials:true});
                setData(res.data);
            }
            catch{
                alert("Can't find user");
                navigate("..");
            }
            finally {
                setLoading(false);
            }
        }
        getData().then();
    }, [search]);
    console.log(data)
    return(
        <div className="w-full p-4 flex flex-col gap-6">
            {loading ? (<div className="m-auto font-bold text-xl">Loading...</div>) : (data.length > 0 ? data.filter(u=>u.id !== user?.id).map((u:User,index:number)=>(
                <div key={index} className="flex items-center border-[#2A2A2A] border-2 p-4 px-[1rem] gap-4 rounded-[1rem] bg-[#1E1E1E] w-[50%] mx-auto">
                    <img alt="user's avatar" className="rounded-full w-[4rem] h-[4rem]"
                         src={u?.avatarUrl ? u.avatarUrl : "/default_avatar.jpg"} />
                    <div className="flex flex-col w-full">
                        <div className="w-full flex items-center">
                            <p className="text-2xl font-medium">{u.username}</p>
                            <p className="text-[#b9babd] ml-auto">Joined at: {new Date(u.createdAt).toLocaleString()}</p>
                        </div>
                        <div className="flex gap-2">
                            <p>
                                {u.followerList.length} follower
                            </p>
                            <p>
                                {u.followingList.length} following
                            </p>
                        </div>
                    </div>
                </div>
            )) : "User not exists")}
        </div>
    )
}