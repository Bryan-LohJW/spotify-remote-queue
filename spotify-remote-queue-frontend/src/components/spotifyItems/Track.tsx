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

	const imageUrl = props.track.album.images[0].url;

	console.log(imageUrl);

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
			className="relative m-2 flex h-20 items-center gap-2 rounded-md bg-gray-800 p-3 text-white md:mx-auto md:h-28 md:w-8/12"
			key={props.track.id}
		>
			<img src={imageUrl} className="aspect-square h-full rounded-sm" />
			<div>
				<div className="flex h-full w-60 flex-col gap-2 md:w-max md:gap-6">
					<p className="truncate whitespace-nowrap text-base font-semibold md:text-lg">
						{props.track.name}
					</p>
					<div className="">
						<div className="flex gap-1">
							{props.track.artists.map((artist, index, array) => (
								<p
									className="truncate whitespace-nowrap text-sm md:text-base"
									key={artist.name}
								>
									{artist.name}
									{array.length - index - 1 ? ' ,' : ''}
								</p>
							))}
						</div>
					</div>
				</div>
			</div>
			<button
				onClick={() => {
					addSongToQueue(props.track.uri);
				}}
				disabled={isAdded}
				className="group"
			>
				<BiPlusCircle className="absolute right-4 h-6 w-6 -translate-y-1/2 group-disabled:text-gray-600 md:h-10 md:w-10"></BiPlusCircle>
			</button>
		</div>
	);
};

export default Track;
