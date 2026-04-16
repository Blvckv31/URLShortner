import { useState } from "react";
import API from "../api/api";

export default function Register() {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const register = async () => {
        try {
            await API.post("/auth/register", {
                username,
                password
            });

            alert("Registered successfully!");
            window.location.href = "/login";

        } catch (err) {
            alert("Registration failed");
        }
    };

    return (
        <div>
            <h2>Register</h2>

            <input placeholder="Username"
                onChange={(e) => setUsername(e.target.value)} />

            <input type="password"
                placeholder="Password"
                onChange={(e) => setPassword(e.target.value)} />

            <button onClick={register}>Register</button>
        </div>
    );
}