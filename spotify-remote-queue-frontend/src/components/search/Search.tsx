import { useForm } from 'react-hook-form';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/store';
import { TracksTrack, type SearchResponse } from '../../types/SearchResponse';
import { useState } from 'react';
import Track from '../spotifyItems/Track';
import { BiSearch } from 'react-icons/bi';

type Inputs = {
	title: string;
};

const Search = () => {
	const [tracks, setTracks] = useState<TracksTrack[]>();
	const { register, handleSubmit } = useForm<Inputs>();
	const { jwt } = useSelector((state: RootState) => state.authentication);
	const accessToken = 'Bearer ' + jwt;
	const onSubmit = async (data: Inputs) => {
		const url =
			import.meta.env.VITE_BACKEND_ENDPOINT_BASE +
			'/api/v1/spotify/search/all';

		const searchQuery = '?query=' + data.title.trim();
		const response = await fetch(url + searchQuery, {
			method: 'GET',
			headers: {
				'x-api-key': import.meta.env.VITE_BACKEND_API_KEY,
				'Content-Type': 'application/json',
				Authorization: accessToken,
			},
		});
		const body = (await response.json()) as SearchResponse;
		setTracks(body.tracks.items);
	};

	return (
		<div>
			<p className="mx-auto w-fit text-2xl text-white">Search</p>
			<div className="h-4"></div>
			<form
				onSubmit={handleSubmit(onSubmit)}
				className="mx-auto flex w-fit gap-4"
			>
				<input
					type="text"
					{...register('title', { required: true })}
					className="rounded-3xl bg-gray-700 px-6 py-2 text-white"
				/>
				<button>
					<BiSearch className="h-8 w-8 text-white"></BiSearch>
				</button>
			</form>
			<div className="h-4"></div>
			<div>
				{tracks?.map((track) => (
					<Track track={track} key={track.id}></Track>
				))}
			</div>
		</div>
	);
};

export default Search;
