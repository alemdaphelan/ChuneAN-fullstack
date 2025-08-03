import {createContext,useContext} from "react";
interface User{
    id:string,
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