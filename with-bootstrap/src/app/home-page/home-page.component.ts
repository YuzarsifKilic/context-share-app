import { Component } from '@angular/core';
import {GameHomeComponent} from "../game/game-home/game-home.component";
import {MatTab, MatTabContent, MatTabGroup} from "@angular/material/tabs";
import {MovieHomeComponent} from "../movie/movie-home/movie-home.component";

@Component({
  selector: 'app-home-page',
  standalone: true,
    imports: [
        GameHomeComponent,
        MatTab,
        MatTabContent,
        MatTabGroup,
        MovieHomeComponent
    ],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {

}
