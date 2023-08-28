import { useForm } from 'react-hook-form';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/store';
import { TracksTrack, type SearchResponse } from '../../types/SearchResponse';
import { useState } from 'react';
import Track from '../spotifyItems/Track';

type Inputs = {
	title: string;
};

const Search = () => {
	const [tracks, setTracks] = useState<TracksTrack[]>();
	const { register, handleSubmit } = useForm<Inputs>();
	const { jwt } = useSelector((state: RootState) => state.authentication);
	const accessToken = 'Bearer ' + jwt;
	const onSubmit = async (data: Inputs) => {
		const url = 'http://localhost:8080/api/v1/spotify/search/all';

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
			<form onSubmit={handleSubmit(onSubmit)}>
				<div>
					<input
						type="text"
						{...register('title', { required: true })}
					/>
					<button>Search</button>
				</div>
			</form>
			<div>
				{tracks?.map((track) => (
					<Track track={track}></Track>
				))}
			</div>
		</div>
	);
};

export default Search;
