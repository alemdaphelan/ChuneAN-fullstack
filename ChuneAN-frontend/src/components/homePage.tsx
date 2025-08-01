import {useEffect,useState} from 'react'
import {useNavigate, Link, Outlet} from 'react-router-dom'
import {UserContext} from "./userContext.tsx";
import axios from 'axios'

interface User{
    userId:string,
    username:string,
    email:string,
    tokenCount:number,
    bio:string,
    birthday:Date,
    createdAt:Date,
    avatarUrl:string
}

export default function HomePage(){
    const navigate = useNavigate();
    const [data,setData] = useState<User | null>(null);
    useEffect(()=>{
        const getData = async () =>{
            try {
                const response = await axios.get('http://localhost:8080/api/users/info', {
                    withCredentials: true
                });
                if(response.status === 200 || response.status === 201){
                    setData(response.data);
                }
                else{
                    navigate('/');
                }
            }
            catch(err){
                console.error(err);
                navigate('/');
            }
        }
        getData().then();
        return;
    },[]);
    return(
        <div className="text-white bg-black min-h-screen">
            <header className="flex px-[1rem] pb-[.5rem] gap-[2rem] items-center pl-[7rem] min-w-fullscreen">
                <div className="flex gap-[1rem] pr-[4rem] items-center">
                    <img alt="logo" src="/logo.jpg" className="w-[3rem] h-[3rem]"/>
                    <Link className="text-[2rem] font-semibold" to='/'>ChuneAN</Link>
                </div>
                <Link className="text-[#b9babd] hover:text-white" to="feed">Feed</Link>
                <Link className="text-[#b9babd] hover:text-white" to="library">Library</Link>
                <Link className="text-[#b9babd] hover:text-white" to="studio">Create Music</Link>
                <div className="flex text-[1rem] gap-[1rem] ml-auto select-none">
                    <div className="flex gap-1 items-center">
                        <p>{data?.tokenCount ? data.tokenCount : 0}</p>
                        <img src="/token.png" alt="token" className="w-[1rem] h-[1rem]" />
                    </div>
                    <div className="flex items-center gap-2">
                        <p className="">{data?.username}</p>
                        <img alt="user's avatar" src={data?.avatarUrl ? data.avatarUrl : "/default_avatar.jpg"} className="w-[2rem] h-[2rem] rounded-full" />
                    </div>
                </div>
            </header>
            <UserContext.Provider value={data}>
                <Outlet/>
            </UserContext.Provider>
        </div>
    )
}