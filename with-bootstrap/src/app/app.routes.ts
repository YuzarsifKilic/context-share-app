import { Routes } from '@angular/router';
import {GameHomeComponent} from "./game/game-home/game-home.component";
import {LoginComponent} from "./login/login.component";
import {MovieHomeComponent} from "./movie/movie-home/movie-home.component";
import {GameDetailsComponent} from "./game/game-details/game-details.component";
import {HomePageComponent} from "./home-page/home-page.component";

export const routes: Routes = [
  {
    path: '',
    component: HomePageComponent
  },
  {
    path: 'game',
    component: GameHomeComponent
  },
  {
    path: 'game-details/:id',
    component: GameDetailsComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'movie',
    component: MovieHomeComponent
  },
  {
    path: 'admin',
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)
  }
];
