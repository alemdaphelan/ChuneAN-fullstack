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

export interface Following{
    userId:string,
    username:string,
    avatarUrl:string
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

export interface Like{
    userId:string,
    postId:string
}