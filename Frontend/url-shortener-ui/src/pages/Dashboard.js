import { useState } from "react";
import API from "../api/api";

export default function Dashboard() {
	const [url, setUrl] = useState("");
	const [shortUrl, setShortUrl] = useState("");

	const shorten = async () => {
		try {
			const res = await API.post("/shorten", {
				url: url,
			});

			const shortLink = "http://localhost:8080/" + res.data.shortUrl;

			setShortUrl(shortLink);
		} catch (err) {
			alert("Error creating short URL");
		}
	};

	const logout = () => {
		localStorage.removeItem("token");
		window.location.href = "/login";
	};

	const copyToClipboard = () => {
		navigator.clipboard.writeText(shortUrl);
		alert("Copied to clipboard!");
	};

	return (
		<div className="container-fluid vh-100 d-flex justify-content-center align-items-center bg-light">
			<div className="col-lg-4 d-flex justify-content-center align-items-center gradient-custom-2 text-white p-5 rounded shadow">
				<div style={{ width: "100%" }}>
					<h2 className="mb-4 text-center">URL Shortener</h2>

					{/* INPUT */}
					<div className="mb-3">
						<input
							type="text"
							className="form-control"
							placeholder="Enter URL"
							value={url}
							onChange={(e) => setUrl(e.target.value)}
						/>
					</div>

					{/* SHORTEN BUTTON */}
					<button
						onClick={shorten}
						className="btn btn-dark w-100 mb-3"
					>
						Shorten
					</button>

					{/* RESULT */}
					{shortUrl && (
						<div className="text-center">
							<p>Short URL:</p>

							<a
								href={shortUrl}
								target="_blank"
								rel="noopener noreferrer"
								className="text-warning"
							>
								{shortUrl}
							</a>

							<div className="mt-3">
								<button
									onClick={copyToClipboard}
									className="btn btn-outline-light btn-sm"
								>
									Copy
								</button>
							</div>
						</div>
					)}

					<div className="d-flex align-items-center justify-content-center">
						<button
							onClick={logout}
							className="btn btn-light w-25 mt-4"
						>
							Logout
						</button>
					</div>
				</div>
			</div>
		</div>
	);
}