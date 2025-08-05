export interface User{
    id:string,
    username:string,
    email:string,
    tokenCount:number,
    bio:string,
    birthday:Date,
    createdAt:Date,
    avatarUrl:string,
    followingList: Following[],
    followerList: Following[]
}

interface Following{
    id:string,
    userId:string,
    followingId:string
}

export interface Post{
    id:string,
    avatarUrl:string,
    username:string,
    createdAt:string | null,
    title:string,
    content:string,
    trackUrl:string,
    likeCount:number,
    commentCount:number,
    userId:string
}