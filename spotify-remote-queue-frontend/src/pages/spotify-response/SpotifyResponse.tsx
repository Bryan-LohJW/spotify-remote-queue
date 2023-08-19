import { useMutation } from 'react-query';

const SpotifyResponse = () => {
	const mutation = useMutation((spotifyCode: string) => {
		return fetch('http://localhost:8080/api/v1/spotify/authenticate', {
			method: 'POST',
			headers: {
				Credentials: 'include',
				'x-api-key': import.meta.env.VITE_BACKEND_API_KEY,
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({ code: spotifyCode }),
		});
	});

	const triggerLogin = () => {
		const params = new URLSearchParams(document.location.search);
		const code = params.get('code');
		const error = params.get('error');

		if (code && !error) {
			console.log('send to backend');
			mutation.mutate(code);
			console.log(mutation.data);
		}

		if (error) {
			console.log('Process error');
		}
	};

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

		const body = response.json();

		console.log(body);
	};

	const fetchFalse = async () => {
		const response = await fetch(
			'http://localhost:8080/api/v1/spotify/test',
			{
				method: 'GET',
				headers: {
					'Access-Control-Allow-Credentials': 'true',
					credentials: 'include',
					'x-api-key': import.meta.env.VITE_BACKEND_API_KEY,
					'Content-Type': 'application/json',
				},
			}
		);
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
		</>
	);
};

// const sendToBackend = async (spotifyCode: string) => {
// 	const response = await fetch(
// 		'http://localhost:8080/api/v1/spotify/authenticate',
// 		{
// 			method: 'POST',
// 			headers: {
// 				Credentials: 'include',
// 				'x-api-key': import.meta.env.VITE_BACKEND_API_KEY,
// 				'Content-Type': 'application/json',
// 			},
// 			body: JSON.stringify({ code: spotifyCode }),
// 		}
// 	);

// 	if (!response.ok) {
// 		// throw error
// 		console.log(response);
// 	}

// 	const body = await response.json();
// 	console.log(body);
// };

export default SpotifyResponse;
