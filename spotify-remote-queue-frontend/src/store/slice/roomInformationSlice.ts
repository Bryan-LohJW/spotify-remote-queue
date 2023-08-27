import { PayloadAction, createSlice } from '@reduxjs/toolkit';
import { RoomInformation } from '../../pages/home/Home';

export interface roomInformationState {
	roomId: string;
	pin: string;
	expiry: string;
	isActive: boolean;
}

const initialState = {
	roomId: '',
	pin: '',
	expiry: '',
	isActive: false,
};

export const roomInformationSlice = createSlice({
	name: 'roomInformation',
	initialState,
	reducers: {
		saveInformation: (state, action: PayloadAction<RoomInformation>) => {
			state.roomId = action.payload.roomId;
			state.pin = action.payload.pin;
			state.expiry = action.payload.expiry;
			state.isActive = action.payload.active;
		},
	},
});

export const { saveInformation } = roomInformationSlice.actions;

export default roomInformationSlice.reducer;
