import { useState } from 'react';

const SpotifyResponse = () => {
	const [accessToken, setAccessToken] = useState('');

	const fetchFunction = async (spotifyCode: string) => {
		const response = await fetch(
			'http://localhost:8080/api/v1/spotify/registerRoom',
			{
				method: 'POST',
				headers: {
					'Access-Control-Allow-Credentials': 'true',
					credentials: 'include',
					'x-api-key': import.meta.env.VITE_BACKEND_API_KEY,
					'Content-Type': 'application/json',
				},
				body: JSON.stringify({ code: spotifyCode }),
			}
		);

		const body = await response.json();
		setAccessToken(response.headers.get('Authorization') || '');
		console.log(body);
	};

	const fetchFalse = async () => {
		const response = await fetch(
			'http://localhost:8080/api/v1/spotify/test',
			{
				method: 'GET',
				headers: {
					'x-api-key': import.meta.env.VITE_BACKEND_API_KEY,
					'Content-Type': 'application/json',
					Authorization: accessToken,
				},
			}
		);
		const body = await response.json();
		console.log(body);
	};

	const fetchSearch = async (search: string) => {
		const url = 'http://localhost:8080/api/v1/spotify/search';

		const searchQuery = '?query=' + search.trim();
		const response = await fetch(url + searchQuery, {
			method: 'GET',
			headers: {
				'x-api-key': import.meta.env.VITE_BACKEND_API_KEY,
				'Content-Type': 'application/json',
				Authorization: accessToken,
			},
		});
		const body = await response.json();
		console.log(body);
	};

	const fetchRegister = async (
		roomId: string,
		pin: string,
		userId: string
	) => {
		const url = 'http://localhost:8080/api/v1/spotify/registerUser';

		const response = await fetch(url, {
			method: 'POST',
			headers: {
				'x-api-key': import.meta.env.VITE_BACKEND_API_KEY,
				'Content-Type': 'application/json',
				Authorization: accessToken,
			},
			body: JSON.stringify({
				userId,
				pin,
				roomId,
			}),
		});
		const body = await response.json();
		console.log(body);
	};

	const fetchAddToQueue = async (addToQueueInput: string) => {
		const url = 'http://localhost:8080/api/v1/spotify/addToQueue';

		const response = await fetch(url, {
			method: 'POST',
			headers: {
				'x-api-key': import.meta.env.VITE_BACKEND_API_KEY,
				'Content-Type': 'application/json',
				Authorization: accessToken,
			},
			body: JSON.stringify({
				itemUri: addToQueueInput,
			}),
		});
		const body = await response.json();
		console.log(body);
	};

	const params = new URLSearchParams(document.location.search);
	const code = params.get('code');

	return (
		<>
			<p>Hello</p>
			<button
				onClick={() => {
					fetchFunction(code || '');
				}}
			>
				Another
			</button>
			<button
				onClick={() => {
					fetchFalse();
				}}
			>
				Get
			</button>
			<form
				onSubmit={(e) => {
					e.preventDefault();
					const searchInput = document.getElementById(
						'searchInput'
					) as HTMLInputElement;
					fetchSearch(searchInput.value);
				}}
			>
				<input type="text" placeholder="Search" id="searchInput" />
				<button type="submit">Search</button>
			</form>
			<form
				onSubmit={(e) => {
					e.preventDefault();
					const roomIdInput = document.getElementById(
						'roomIdInput'
					) as HTMLInputElement;
					const pinInput = document.getElementById(
						'pinInput'
					) as HTMLInputElement;
					const userIdInput = document.getElementById(
						'userIdInput'
					) as HTMLInputElement;
					fetchRegister(
						roomIdInput.value,
						pinInput.value,
						userIdInput.value
					);
				}}
			>
				<input type="text" placeholder="Room Id" id="roomIdInput" />
				<input type="text" placeholder="Pin" id="pinInput" />
				<input type="text" placeholder="Name" id="userIdInput" />
				<button type="submit">Search</button>
			</form>
			<form
				onSubmit={(e) => {
					e.preventDefault();
					const addToQueueInput = document.getElementById(
						'addToQueueInput'
					) as HTMLInputElement;

					fetchAddToQueue(addToQueueInput.value);
				}}
			>
				<input
					type="text"
					placeholder="TrackUri"
					id="addToQueueInput"
				/>
				<button type="submit">Search</button>
			</form>
		</>
	);
};

export default SpotifyResponse;
