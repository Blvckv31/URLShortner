import { useState } from "react";
import API from "../api/api";
import "../App.css";
import logo from "./Black.png";
import "bootstrap/dist/css/bootstrap.min.css";

export default function Login() {
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");

	const login = async () => {
		try {
			const res = await API.post("/auth/login", {
				username,
				password,
			});

			localStorage.setItem("token", res.data);

			alert("Login successful!");
			window.location.href = "/dashboard";
		} catch (err) {
			alert("Login failed");
		}
	};

	return (
		<div className="container-fluid vh-100 d-flex justify-content-center align-items-center">
			<div
				className="card shadow-lg border-0 overflow-hidden"
				style={{ maxWidth: "900px", width: "100%" }}
			>
				<div className="row g-0">
					{/* LEFT SIDE */}
					<div className="col-lg-6 bg-white">
						<div className="p-5">
							<div className="text-center mb-4">
								<img
									src={logo}
									alt="logo"
									style={{ width: "160px" }}
								/>
								<h4 className="mt-3">Welcome</h4>
							</div>

							<form
								onSubmit={(e) => {
									e.preventDefault();
									login();
								}}
							>
								<div className="mb-3">
									<input
										type="text"
										className="form-control"
										placeholder="Username"
										value={username}
										onChange={(e) => setUsername(e.target.value)}
									/>
								</div>

								<div className="mb-3">
									<input
										type="password"
										className="form-control"
										placeholder="Password"
										value={password}
										onChange={(e) => setPassword(e.target.value)}
									/>
								</div>

								<button
									className="btn btn-dark w-100"
									type="submit"
								>
									Log in
								</button>
							</form>
						</div>
					</div>

					{/* RIGHT SIDE */}
					<div className="col-lg-6 d-flex align-items-center gradient-custom-2 text-white p-5">
						<div>
							<h4 className="mb-3">URL Shortner</h4>
							<p className="small">
								Engineered for speed. Designed for clean links.
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
}
