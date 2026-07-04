function HomePage(){
    const storedUser = localStorage.getItem("user");
    const user = storedUser ? JSON.parse(storedUser) : null;
    return (
        <>
        <h1>Welcome user, {user.email}</h1>
        </>
    )
}

export default HomePage;