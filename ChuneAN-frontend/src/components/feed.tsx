import {Link,Outlet} from 'react-router';
import { LuNotebookPen } from "react-icons/lu";
export default function Feed(){
    return (
        <main className="flex gap-4 p-4 justify-between">
            <div className="flex flex-col gap-[2rem] bg-[#0a0b0d] p-[1rem] px-[2rem] min-w-[12rem] w-[20%] min-h-screen rounded-[7px]">
                <div className="flex flex-col gap-[1rem]">
                    <p className="text-xl font-semibold">Trending</p>
                    <div className="flex flex-col gap-[.5rem] pl-[.5rem] text-[#b9babd]">
                        <p className="cursor-pointer hover:bg-[#292b33] p-2 px-[.5rem] rounded-[7px]">Top</p>
                        <p className="cursor-pointer hover:bg-[#292b33] p-2 px-[.5rem] rounded-[7px]">Newest</p>
                    </div>
                </div>
                <div className="flex flex-col gap-[1rem]">
                    <p className="text-xl font-semibold">Follow</p>
                    <div className="flex flex-col gap-[.5rem] pl-[.5rem] text-[#b9babd]">
                        <p className="cursor-pointer hover:bg-[#292b33] p-2 px-[.5rem] rounded-[7px]">Following</p>
                        <p className="cursor-pointer hover:bg-[#292b33] p-2 px-[.5rem] rounded-[7px]">Follower</p>
                    </div>
                </div>
                <Link to="create" className="flex justify-center items-center bg-red-700 hover:bg-red-900 text-xl p-2 p-x-[1rem] rounded-[100vw] font-medium gap-2">
                    <LuNotebookPen className="text-xl"/>
                    <p>What's new?</p>
                </Link>
                <Outlet/>
            </div>
            <div className="">
                this is feed
            </div>
            <div className="flex flex-col gap-[2rem] bg-[#0a0b0d] p-[1rem] px-[2rem] min-w-[12rem] w-[20%] min-h-screen rounded-[7px]">
                <div>
                    <p className="text-xl font-semibold text-center">Recommended to follow</p>
                </div>
                <div>
                    <p className="text-xl font-semibold text-center">Recommended Bands</p>
                </div>
            </div>
            </main>
    )
}