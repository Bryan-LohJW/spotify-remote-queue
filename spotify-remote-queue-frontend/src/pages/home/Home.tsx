const Home = () => {
	const authEndpoint = 'https://accounts.spotify.com/authorize';

	return (
		<>
			<p>Hello</p>
			<a
				href={`${authEndpoint}
				?client_id=${import.meta.env.VITE_SPOTIFY_CLIENT_ID}
				&redirect_uri=${import.meta.env.VITE_SPOTIFY_REDIRECT_URI}
				&response_type=code
				&show_dialog=true`}
			>
				Sign in to spotify
			</a>
		</>
	);
};

export default Home;
