import {useState, useEffect,useRef} from 'react';
import axios from 'axios';
import {useNavigate} from "react-router-dom";

export default function PostForm(){
    const formRef = useRef<HTMLDivElement>(null);
    const [title,setTitle] = useState<string>("");
    const [content,setContent] = useState<string>("");
    const navigate = useNavigate();
    useEffect({}=>{

    })
    const handlePost = async() =>{
        const res = axios.post("http://localhost:8080/api/users/posts");
    }
    return (
        <div className="fixed top-[50%] left-[50%] translate-x-[-50%] translate-y-[-50%]">
            <div>

            </div>
        </div>
    )
}