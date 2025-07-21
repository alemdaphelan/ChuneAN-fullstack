import { Outlet,Link } from "react-router-dom";
import { IoIosMail } from "react-icons/io";
import { FaPhoneAlt } from "react-icons/fa";
import { FaFacebook } from "react-icons/fa";
import { FaLocationDot } from "react-icons/fa6";
import { FaStar } from "react-icons/fa";

function LandingPage(){
    
    return(
        <div className=" text-white p-[1rem] pb-[3rem] bg-black">
            <header className="mb-[2rem] text-center flex flex-col gap-[1.5rem]">
                <h1 className="text-5xl font-bold">Make music connect with others</h1>
                <p className="text-[#B3B3B3]">Create, share, and discover music with Bandalem</p>
                <Link to="/auth" className="font-bold rounded-[10px] text-black px-[1rem] py-[0.5rem] bg-[#1DB954] w-[10rem] m-auto cursor-pointer hover:scale-105">GET STARTED</Link>
                <Outlet/>
            </header>
            <main className="mb-[3rem]">
                <div className="flex gap-[1rem] justify-center mb-[5rem] align-middle max-w-[1024px] m-auto h-[25rem]">
                    <div className="border-[#242424] border-[5px] rounded-[5px] w-[60%]">
                        <img alt="user's view" src="/view_user.png" className="w-full h-full object-fill"/>
                    </div>
                    <div className="border-[#242424] border-[5px] rounded-[5px] w-[40%]">
                        <img alt="studio's view" src="/studio_view.png" className="w-full h-full object-fill"/>
                    </div>
                </div>
                <div className="grid grid-rows-2 grid-cols-2 text-[1rem] max-w-[600px] m-auto text-center">
                    <div className="flex gap-[1rem]">
                        <IoIosMail className="text-[1.5rem]"/>
                        <p>bandalem19072025@gmail.com</p>
                    </div>
                    <div className="flex gap-[1rem]">
                        <FaPhoneAlt className="text-[1.5rem]"/>
                        <p>+84 (123456789)</p>
                    </div>
                    <div className="flex gap-[1rem]">
                        <FaFacebook className="text-[1.5rem]"/>
                        <p>Bandalem FnD</p>
                    </div>
                    <div className="flex gap-[1rem]">
                        <FaLocationDot className="text-[1.5rem]"/>
                        <p>666 Lân Trọng Tế, Phường Tây Thạnh, Quận Tân Phú, Hồ Chí Minh</p>
                    </div>
                </div>
            </main>
            <footer>
                <div className="text-yellow-300 flex gap-[1.5rem] text-[1.5rem] justify-center mb-[2rem]">
                    <FaStar />
                    <FaStar />
                    <FaStar />
                    <FaStar />
                    <FaStar />
                </div>
                <div className="flex gap-[2rem] text-xl justify-center text-center">
                    <p>
                        "Great platform for musicians"
                    </p>
                    <p>
                        "Amazing tools and community"
                    </p>
                    <p>
                        "Easy to use and intultitves"
                    </p>
                </div>
            </footer>
        </div>
    )
}

export default LandingPage;