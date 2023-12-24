const Landing = () => {
	// getting reference from https://unbounce.com/landing-page-examples/best-landing-page-examples/#example-calm
	return (
		<div>
			<main className="relative flex h-[52rem] flex-col gap-8 bg-gradient-to-b from-lightblack from-60% to-green pl-16 pt-14 text-white">
				<img
					src="assets/images/title-font.png"
					alt="Remote Queue for Spotify"
					className="mb-3 w-[28rem]"
				/>
				<h2 className="text-3xl">
					<p>
						Break free from the confines of solo listening.
						<br />
						With Remote Queue, the best of Spotify
						<br />
						becomes a shared symphony.
					</p>
				</h2>
				<p className="w-[28rem] flex-wrap text-lg">
					Setting up a session is a breeze – just a few clicks and
					you're ready to synchronize your beats. Share the session
					link with your friends, and watch as the music diversity
					explodes. From old-school classics to the latest hits,
					everyone gets a say in the playlist without any signup fuss!
				</p>
				<button className="h-10 w-52 rounded-full bg-white text-black">
					<p className="font-bold">GET STARTED</p>
				</button>

				<img
					src="assets/images/spotify-image.webp"
					className="fixed right-16"
				/>
			</main>
		</div>
	);
};

export default Landing;
