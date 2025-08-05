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
                const res = await axios.post("http://localhost:8080/api/users/find",search,{withCredentials:true});
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
    return(
        <div className="w-full p-4 flex">
            {loading ? (<div className="m-auto font-bold text-xl">Loading...</div>) : (data.length > 0 ? data.filter(u=>u.id !== user?.id).map((u:User,index:number)=>(
                <div key={index} className="flex items-center p-2 px-[1rem] w-full gap-4">
                    <img alt="user's avatar" className="rounded-full w-[2rem] h-[2rem]"
                         src={u.avatarUrl || "default_avatar.jpg"} />
                    <div className="flex flex-col">
                        <p className="text-xl font-medium">{u.username}</p>
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