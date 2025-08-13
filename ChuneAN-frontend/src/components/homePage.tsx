import {useEffect,useState,useRef} from 'react'
import {useNavigate, Link, Outlet} from 'react-router-dom'
import {UserContext, SearchingContext} from "./userContext.tsx";
import axios from 'axios'
import type {User} from './Interfaces';

export default function HomePage(){
    const navigate = useNavigate();
    const [data,setData] = useState<User | null>(null);
    const [open,setOpen] = useState<boolean>(false);
    const [search,setSearch] = useState<string>("");
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

    useEffect(() => {
        let url:string | null = null;
        const getAvatar = async() =>{
            try{
                const res = await axios.get('http://localhost:8080/api/users/avatar',{responseType:"blob",withCredentials: true});
                url = URL.createObjectURL(res.data);
                setData((prev):User | null => {
                    if(!prev || !url) return null;
                    return ({...prev,avatarUrl:(url ? url : "/default_avatar")});
                });
            }
            catch (e){
                console.error(e);
                setData(prev => {
                    if(!prev) return null
                    return ({...prev,avatarUrl:"/default_avatar.jpg"});
                });
            }
        }
        getAvatar().then();
        return () => {if(url != null) URL.revokeObjectURL(url)};
    }, []);

    const handleLogout = async () =>{
        const res = await axios.get("http://localhost:8080/api/users/logout",{withCredentials:true});
        if(res.status === 200){
            navigate("/");
        }
    }
    console.log(data?.avatarUrl);
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
                <div className="relative w-[20rem] h-[3rem] rounded-[100vw] bg-[#1E1E1E] overflow-hidden border-2 border-[#2A2A2A]">
                    <input placeholder="Search" className=" w-[70%] h-full focus:outline-0 px-4" onChange={(e)=>setSearch(e.target.value)}/>
                    <Link to="search" className="text-black rounded-[100vw] p-2 px-[1rem] font-medium bg-white absolute h-full right-0">Search</Link>
                </div>
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
            <SearchingContext.Provider value={search}>
                <UserContext.Provider value={data}>
                    <Outlet/>
                </UserContext.Provider>
            </SearchingContext.Provider>
        </div>
    )
}