import { Routes } from '@angular/router';
import {GameHomeComponent} from "./game/game-home/game-home.component";
import {LoginComponent} from "./login/login.component";
import {MovieHomeComponent} from "./movie/movie-home/movie-home.component";

export const routes: Routes = [
  {
    path: 'game',
    component: GameHomeComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'movie',
    component: MovieHomeComponent
  }
];
