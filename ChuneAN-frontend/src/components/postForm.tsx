import {useState, useEffect,useRef} from 'react';
import {useNavigate} from "react-router-dom";
import {useUser} from "./userContext.tsx";
import axios from "axios";
export default function PostForm(){
    const formRef = useRef<HTMLDivElement>(null);
    const [title,setTitle] = useState<string>("");
    const [content,setContent] = useState<string>("");
    const [file,setFile] = useState<File | null>(null);
    const navigate = useNavigate();
    const user = useUser();

    useEffect(() => {
        const handleClickOutside = (e: MouseEvent) =>{
            if(formRef.current && !formRef.current.contains(e.target as Node)){
                navigate('..');
            }
        }
        document.addEventListener("mousedown",handleClickOutside);
        return () => document.removeEventListener("mousedown",handleClickOutside);
    },[navigate]);

    const handleChange = (text:string , size:number, type:string) : void =>{
        if(text.length > size){
            alert(type + "'s character number must be less than " + size);
            return;
        }
        if(type === "title"){
            setTitle(text);
        }
        if(type == "content"){
            setContent(text);
        }
    }
    const handleChangeFile = (e: React.ChangeEvent<HTMLInputElement>) : void =>{
        if(e.target.files && e.target.files[0]){
            const selectedFile = e.target.files[0];
            if(!selectedFile.type.startsWith("audio")){
                alert("Only audio files are allowed");
                return;
            }
            setFile(selectedFile);
        }
    }
    const handlePost = async () =>{
        if(!title || !content){
            alert("Required field is empty");
            return;
        }
        const formData = new FormData();
        formData.append("userId",user?.userId ? user.userId : "");
        formData.append("title",title);
        formData.append("content",content);
        formData.append("file",file as Blob);

        const res = await axios.post("http://localhost:8080/api/users/posts",formData,{
            headers:{
                "Content-type":"multipart/form-data"
            },
            withCredentials:true
        });
        alert(res.data);
        navigate("/feed");
    }
    return (
        <div ref={formRef} className="fixed top-[50%] left-[50%] translate-x-[-50%] translate-y-[-50%] flex flex-col gap-[2rem] bg-[#1E1E1E] p-[3rem] rounded-[2rem] border-[2px] border-[#2A2A2A]">
            <div className="flex items-center gap-[2rem]">
                <img src={user?.avatarUrl ? user.avatarUrl : "/default_avatar.jpg"} alt="user's avatar" className="w-[3rem] h-[3rem] rounded-full"/>
                <div>
                    <p className="font-medium text-xl text-[#EAEAEA]">{user?.username}</p>
                    <p className="text-[#B3B3B3]">Create a new post</p>
                </div>
            </div>
            <div className="flex flex-col gap-[1rem]">
                <textarea placeholder="Title" onChange={(e)=>handleChange(e.target.value,30,"title")} className="p-2 bg-[#2A2A2A] focus:outline-0 border-1 border-white rounded-md"></textarea>
                <textarea placeholder="Content" onChange={(e) => handleChange(e.target.value,250,"content")} className="p-2 bg-[#2A2A2A] focus:outline-0 border-1 border-white rounded-md"></textarea>
                <div className="border-b-[3px] mt-[.5rem] border-[#2A2A2A]"></div>
                <input type="file" accept="audio/*" title="Choose file music" onChange={handleChangeFile} className="cursor-pointer hover:underline"/>
            </div>
            <button className="bg-red-700 hover:bg-red-900 rounded-[100vw] cursor-pointer p-2 px-[2rem] ml-auto font-medium " onClick={handlePost}>Post</button>
        </div>
    )
}