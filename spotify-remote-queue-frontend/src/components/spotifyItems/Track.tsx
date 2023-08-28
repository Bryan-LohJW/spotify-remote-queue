import { useSelector } from 'react-redux';
import { TracksTrack } from '../../types/SearchResponse';
import { RootState } from '../../store/store';

type Props = {
	track: TracksTrack;
};

const Track = (props: Props) => {
	const { jwt } = useSelector((state: RootState) => state.authentication);
	const accessToken = 'Bearer ' + jwt;

	const addSongToQueue = async (trackId: string) => {
		const url = 'http://localhost:8080/api/v1/spotify/player/add';

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
		const body = await response.json();
		console.log(body);
	};

	return (
		<div className="text-white">
			<p>{props.track.name}</p>
			<button
				onClick={() => {
					addSongToQueue(props.track.uri);
				}}
			>
				Add to queue
			</button>
		</div>
	);
};

export default Track;
