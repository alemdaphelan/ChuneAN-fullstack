import {useEffect,useState} from 'react'
import {useNavigate, Link, Outlet} from 'react-router-dom'
import axios from 'axios'
export default function HomePage(){
    const navigate = useNavigate();
    const [_data,_setData] = useState<any[]>([]);
    useEffect(()=>{
        const getData = async () =>{
            try {
                const response = await axios.get('http://localhost:8080/api/users/me', {
                    withCredentials: true
                });
                if(response.status === 200 || response.status === 201){
                    _setData(response.data);
                    navigate('/home');
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
        getData();
        return;
    },[]);
    return(
        <div className="text-white bg-black min-h-screen">
            <header className="flex px-[1rem] pb-[.5rem] gap-[2rem] items-center pl-[7rem]">
                <div className="flex gap-[1rem] pr-[4rem] item-center">
                    <img alt="logo" src="/logo.jpg" className="w-[3rem] h-[3rem]"/>
                    <Link className="text-[2rem] font-semibold" to='/'>ChuneAN</Link>
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