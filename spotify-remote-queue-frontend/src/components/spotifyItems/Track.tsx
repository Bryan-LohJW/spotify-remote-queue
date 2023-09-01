import { useSelector } from 'react-redux';
import { TracksTrack } from '../../types/SearchResponse';
import { RootState } from '../../store/store';
import { BiPlusCircle } from 'react-icons/bi';
import { useState } from 'react';

type Props = {
	track: TracksTrack;
};

const Track = (props: Props) => {
	const { jwt } = useSelector((state: RootState) => state.authentication);
	const [isAdded, setIsAdded] = useState(false);

	const accessToken = 'Bearer ' + jwt;

	const addSongToQueue = async (trackId: string) => {
		const url =
			import.meta.env.VITE_BACKEND_ENDPOINT_BASE +
			'/api/v1/spotify/player/add';

		const response = await fetch(url, {
			method: 'POST',
			headers: {
				'x-api-key': import.meta.env.VITE_BACKEND_API_KEY,
				'Content-Type': 'application/json',
				Authorization: accessToken,
			},
			body: JSON.stringify({
				itemUri: trackId,
			}),
		});
		if (response.ok) {
			setIsAdded(true);
		}
	};

	return (
		<div
			className="relative m-2 flex bg-gray-800 p-3 text-white"
			key={props.track.id}
		>
			<div className="flex flex-col gap-5">
				<p className="text-base font-semibold">{props.track.name}</p>
				<div className="flex gap-1">
					{props.track.artists.map((artist, index, array) => (
						<p className="text-sm" key={artist.name}>
							{artist.name}
							{array.length - index - 1 ? ' ,' : ''}
						</p>
					))}
				</div>
			</div>
			<button
				onClick={() => {
					addSongToQueue(props.track.uri);
				}}
				disabled={isAdded}
				className="group"
			>
				<BiPlusCircle className="absolute right-4 h-6 w-6 -translate-y-1/2 group-disabled:text-gray-600"></BiPlusCircle>
			</button>
		</div>
	);
};

export default Track;
