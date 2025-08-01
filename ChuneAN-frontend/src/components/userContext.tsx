import {createContext,useContext} from "react";
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
export const UserContext = createContext<User | null>(null);
export const useUser = () => useContext(UserContext);