import { FaFacebookF } from "react-icons/fa";
import { FaGoogle } from "react-icons/fa";
import { FaInstagram } from "react-icons/fa";
import {useState, useRef, useEffect, type ChangeEvent} from "react"
import { useNavigate } from "react-router-dom";
import axios from "axios";
import * as React from "react";
export default function Login() {
    const formRef = useRef<HTMLDivElement>(null);
    const [usernameOrEmail,setUserNameOrEmail] = useState("");
    const [password,setPassword] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        const handleClickOutSide = (e: MouseEvent) => {
            if (formRef.current && !formRef.current.contains(e.target as Node)) {
                navigate('/');
            }
        };
        document.addEventListener('mousedown', handleClickOutSide);
        return () => {
            document.removeEventListener('mousedown', handleClickOutSide);
        };
    }, [navigate]);

    const handleLogIn = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        if(!usernameOrEmail || !password){
            alert("Vui long tai khoan hoac mat khau");
            return;
        }
        try{
            const res = await axios.post(`http://localhost:8080/api/users/login`,{
                usernameOrEmail,
                password
            });

            if(res.status === 200){
                const data = res.data;
                localStorage.setItem("user",data);
                navigate("/home");
            }
        }
        catch(err){
            alert("Khong the dang nhap");
            console.error(err);
        }
    }
    const [username,setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [birthday,setBirthday] = useState("");
    const handleSignIn = async (e: React.MouseEvent<HTMLButtonElement>) =>{
        e.preventDefault();
        if(!username || !email || !birthday || !password){
            alert("Vui long dien day du thong tin");
            return;
        }
        try{
            const res = await axios.post("http://localhost:8080/api/users/signin",{
                username,
                email,
                password,
                birthday
            });
            if(res.status === 200){
                const data = res.data;
                localStorage.setItem("user",data);
                navigate("/home");
            }
        }
        catch(err){
            console.error(err);
            alert("Khong the dang ky");
        }
    }

    const [logIn, setLogIn] = useState(false);
    return !logIn ? (
        <div ref={formRef} className="bg-[#121212] fixed top-[50%] left-[50%] translate-x-[-50%] translate-y-[-50%] p-[2rem] px-[4rem] rounded-[1rem] flex flex-col gap-[2rem]
        text-[#EAEAEA]">
            <h1 className="text-4xl font-bold">
                Sign up
            </h1>
            <form className="flex flex-col gap-[1rem]">
                <input onChange={(e:ChangeEvent<HTMLInputElement>)=>setUsername(e.target.value)} name="username" type="text"
                    placeholder="Username"
                    className="border-[3px] border-[#2A2A2A] bg-[#1E1E1E] placeholder-[#B3B3B3] w-[15rem] h-[3rem] px-[1rem] rounded-[10px]" />
                <input onChange={(e:ChangeEvent<HTMLInputElement>)=>setEmail(e.target.value)} name="email" placeholder="Email" type="email" className="border-[3px] border-[#2A2A2A] bg-[#1E1E1E] placeholder-[#B3B3B3] w-[15rem] h-[3rem] px-[1rem] rounded-[10px]" />
                <input onChange={(e:ChangeEvent<HTMLInputElement>)=>setPassword(e.target.value)} name="password" placeholder="Password" type="password" className="border-[3px] border-[#2A2A2A] bg-[#1E1E1E] placeholder-[#B3B3B3] w-[15rem] h-[3rem] px-[1rem] rounded-[10px]" />
                <input onChange={(e:ChangeEvent<HTMLInputElement>)=>setBirthday(e.target.value)} name="birthday" placeholder="birthday" type="text" className="border-[3px] border-[#2A2A2A] bg-[#1E1E1E] placeholder-[#B3B3B3] w-[15rem] h-[3rem] px-[1rem] rounded-[10px]" />
                <button onClick={handleSignIn} className="w-full h-full bg-[#4E6EFF] py-[.5rem] cursor-pointer hover:bg-[#4CFFA8] hover:text-[black] rounded-[10px] text-xl">Sign up</button>
            </form>
            <div className="flex justify-center gap-[.5rem] text-[#B3B3B3]">
                <div className="border-b-[2px] border-[#2A2A2A] w-[6.5rem]"></div>
                <p className="text-[12px]">Or</p>
                <div className="border-b-[2px] border-[#2A2A2A] w-[6.5rem]"></div>
            </div>
            <div className="flex justify-center gap-[1rem]">
                <a href="/auth/google" className="p-[1rem] bg-[#1E1E1E] rounded-[5px] border-[2px] border-[#2A2A2A] hover:bg-[#4CFFA8] hover:text-black">
                    <FaGoogle />
                </a>
                <a href="/auth/facebook" className="p-[1rem] bg-[#1E1E1E] rounded-[5px] border-[2px] border-[#2A2A2A] hover:bg-[#4CFFA8] hover:text-black">
                    <FaFacebookF />
                </a>
                <a href="/auth/instagram" className="p-[1rem] bg-[#1E1E1E] rounded-[5px] border-[2px] border-[#2A2A2A] hover:bg-[#4CFFA8] hover:text-black">
                    <FaInstagram />
                </a>
            </div>
            <div>
                <p onClick={() => setLogIn(!logIn)} className="underline cursor-pointer">I had an account</p>
            </div>
        </div>
    ) : (
        <div ref={formRef} className="bg-[#121212] fixed top-[50%] left-[50%] translate-x-[-50%] translate-y-[-50%] p-[2rem] px-[4rem] rounded-[1rem] flex flex-col gap-[2rem]
        text-[#EAEAEA]">
            <h1 className="text-4xl font-bold">
                Log in
            </h1>
            <form className="flex flex-col gap-[1rem]">
                <input onChange={(e)=>setUserNameOrEmail(e.target.value)} name="usernameOrEmail" type="text"
                    placeholder="Username or email"
                    className="border-[3px] border-[#2A2A2A] bg-[#1E1E1E] placeholder-[#B3B3B3] w-[15rem] h-[3rem] px-[1rem] rounded-[10px]" />
                <input onChange={e=>setPassword(e.target.value)} name="password" placeholder="Password" type="password" className="border-[3px] border-[#2A2A2A] bg-[#1E1E1E] placeholder-[#B3B3B3] w-[15rem] h-[3rem] px-[1rem] rounded-[10px]" />
                <button className="w-full h-full bg-[#4E6EFF] py-[.5rem] cursor-pointer hover:bg-[#4CFFA8] hover:text-[black] rounded-[10px] text-xl" onClick={handleLogIn}>Log in</button>
            </form>
            <div className="flex justify-center gap-[.5rem] text-[#B3B3B3]">
                <div className="border-b-[2px] border-[#2A2A2A] w-[6.5rem]"></div>
                <p className="text-[12px]">Or</p>
                <div className="border-b-[2px] border-[#2A2A2A] w-[6.5rem]"></div>
            </div>
            <div className="flex justify-center gap-[1rem]">
                <a href="/auth/google" className="p-[1rem] bg-[#1E1E1E] rounded-[5px] border-[2px] border-[#2A2A2A] hover:bg-[#4CFFA8] hover:text-black">
                    <FaGoogle />
                </a>
                <a href="/auth/facebook" className="p-[1rem] bg-[#1E1E1E] rounded-[5px] border-[2px] border-[#2A2A2A] hover:bg-[#4CFFA8] hover:text-black">
                    <FaFacebookF />
                </a>
                <a href="/auth/instagram" className="p-[1rem] bg-[#1E1E1E] rounded-[5px] border-[2px] border-[#2A2A2A] hover:bg-[#4CFFA8] hover:text-black">
                    <FaInstagram />
                </a>
            </div>
            <div>
                <p onClick={() => setLogIn(!logIn)} className="underline cursor-pointer">I don't have an account</p>
            </div>
        </div>
    );
}