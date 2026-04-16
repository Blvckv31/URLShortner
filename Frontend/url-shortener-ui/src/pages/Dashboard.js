import { useState } from "react";
import API from "../api/api";

export default function Dashboard() {

    const [url, setUrl] = useState("");
    const [shortUrl, setShortUrl] = useState("");

    const shorten = async () => {
        try {
            const res = await API.post("/url/shorten", {
                originalUrl: url
            });

            setShortUrl(res.data.shortUrl);

        } catch (err) {
            alert("Error creating short URL");
        }
    };

    const logout = () => {
        localStorage.removeItem("token");
        window.location.href = "/login";
    };

    return (
        <div>
            <h2>Dashboard</h2>

            <input
                placeholder="Enter URL"
                onChange={(e) => setUrl(e.target.value)}
            />

            <button onClick={shorten}>Shorten</button>

            {shortUrl && (
                <p>Short URL: {shortUrl}</p>
            )}

            <button onClick={logout}>Logout</button>
        </div>
    );
}