import {useEffect,useState} from 'react'
import {useNavigate, Link, Outlet} from 'react-router-dom'
export default function HomePage(){
    const navigate = useNavigate();
    const [_data,setData] = useState<any[]>([]);
    useEffect(()=>{
        const store = localStorage.getItem("user");
        if(!store){
            navigate('/');
            return;
        }
        const user = JSON.parse(store);
        if(!user){
            navigate('/');
            return;
        }
        const getData = async() =>{
            try{const res = await fetch('http://localhost/users');
                const json = await res.json();
                if(!Array.isArray(json) || !json.length){
                    return
                }
                setData(json);
            }
            catch(err){
                console.error("error: false to get data",err);
            }
        }
        getData();
    });
    return(
        <div className="text-white bg-black min-h-screen">
            <header className="flex px-[1rem] pb-[.5rem] gap-[2rem] items-center pl-[7rem]">
                <div className="flex gap-[1rem] pr-[4rem] item-center">
                    <img alt="logo" src="src\assets\logo.jpg" className="w-[3rem] h-[3rem]"/>
                    <Link className="text-[2rem] font-semibold" to='/'>Bandalem</Link>
                </div>
                <Link className="text-[#b9babd] hover:text-white" to="feed">Feed</Link>
                <Link className="text-[#b9babd] hover:text-white" to="library">Library</Link>
                <Link className="text-[#b9babd] hover:text-white" to="studio">Create Music</Link>
                <div>

                </div>
            </header>
            <Outlet/>
        </div>
    )
}