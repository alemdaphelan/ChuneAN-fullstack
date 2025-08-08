import LandingPage from './components/landingPage'
import Login from './components/authForm' 
import Feed from './components/feed';
import Library from './components/library';
import Studio from './components/studio';
import {BrowserRouter,Routes,Route,Navigate} from 'react-router-dom'
import HomePage from './components/homePage'
import PostForm from "./components/postForm.tsx";
import MyInfo from "./components/myInfo.tsx";
import UserResult from "./components/findUserResult.tsx";
function App() {

  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route>
            <Route path="/" element={<LandingPage/>}>
              <Route path="auth" element={<Login/>}></Route>
            </Route>
            <Route path="/home" element={<HomePage/>}>
              <Route index element={<Navigate to="feed" replace/>} />
              <Route path="feed" element={<Feed/>}>
                <Route path="create" element={<PostForm/>}></Route>
              </Route>
              <Route path="library" element={<Library/>}></Route>
              <Route path="studio" element={<Studio/>}></Route>
              <Route path="search" element={<UserResult/>}></Route>
            </Route>
            <Route path="/myInfo" element={<MyInfo/>}>
              <Route path="edit"></Route>
            </Route>
          </Route>
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
