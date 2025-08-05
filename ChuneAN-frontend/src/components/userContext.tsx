import {createContext,useContext} from "react";
import type {User} from './Interfaces.tsx'

export const UserContext = createContext<User | null>(null);
export const useUser = () => useContext(UserContext);

export const SearchingContext = createContext<string>("");
export const searchUser = () => useContext(SearchingContext);