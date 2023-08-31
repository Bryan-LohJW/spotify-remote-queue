import { createSlice } from '@reduxjs/toolkit';
import type { PayloadAction } from '@reduxjs/toolkit';
import { decodeJwt } from 'jose';

type Claims = {
	username: string;
	roomId: string;
	authorities: string;
	exp: number;
};

export interface AuthenticationState {
	jwt: string;
	isAuthenticated: boolean;
	userId: string;
	roomId: string;
	authorities: string;
	exp: number;
}

const initialState: AuthenticationState = {
	jwt: '',
	isAuthenticated: false,
	userId: '',
	roomId: '',
	authorities: '',
	exp: 0,
};

export const authenticationSlice = createSlice({
	name: 'authentication',
	initialState,
	reducers: {
		authenticate: (state) => {
			state.isAuthenticated = true;
		},
		revokeAuthentication: (state) => {
			state.isAuthenticated = false;
		},
		saveJwt: (state, action: PayloadAction<string>) => {
			const claims = decodeJwt(action.payload) as Claims;
			state.jwt = action.payload;
			state.userId = claims.username;
			state.roomId = claims.roomId;
			state.exp = claims.exp;
			state.authorities = claims.authorities;
		},
	},
});

export const { authenticate, revokeAuthentication, saveJwt } =
	authenticationSlice.actions;

export default authenticationSlice.reducer;
