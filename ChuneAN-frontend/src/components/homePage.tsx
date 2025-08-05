import {useEffect,useState,useRef} from 'react'
import {useNavigate, Link, Outlet} from 'react-router-dom'
import {UserContext} from "./userContext.tsx";
import axios from 'axios'
import type {User} from './Interfaces';

export default function HomePage(){
    const navigate = useNavigate();
    const [data,setData] = useState<User | null>(null);
    const [open,setOpen] = useState<boolean>(false);
    const menuRef = useRef<HTMLDivElement>(null);
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
        const handleClickOutSide = (e:MouseEvent) =>{
            if(menuRef.current && !(menuRef.current as HTMLElement).contains(e.target as Node)){
                setOpen(false);
            }
        }
        getData().then();
        document.addEventListener("mousedown",handleClickOutSide);
        return ()=>document.removeEventListener("mousedown",handleClickOutSide);
    },[]);
    const handleLogout = async () =>{
        const res = await axios.get("http://localhost:8080/api/users/logout",{withCredentials:true});
        if(res.status === 200){
            navigate("/");
        }
    }
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
                        <div ref={menuRef} className="relative">
                            <img onClick={()=> setOpen(!open)} loading="lazy" alt="user's avatar" src={data?.avatarUrl ? data.avatarUrl : "/default_avatar.jpg"} className="cursor-pointer w-[2rem] h-[2rem] rounded-full" />
                            {open ?
                                <div className="absolute right-0 mt-2 flex flex-col bg-white text-black rounded-md overflow-hidden cursor-pointer min-w-max">
                                    <a href="http://localhost:5173/myInfo" className="hover:bg-gray-400 p-2 px-[1rem] text-center">Profile</a>
                                    <a onClick={handleLogout} className="hover:bg-gray-400 p-2 px-[1rem] text-center">Logout</a>
                                </div>
                                : ""
                            }
                        </div>
                    </div>
                </div>
            </header>
            <UserContext.Provider value={data}>
                <Outlet/>
            </UserContext.Provider>
        </div>
    )
}