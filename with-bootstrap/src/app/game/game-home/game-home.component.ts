import {AfterViewInit, Component} from '@angular/core';
import {MovieService} from "../../_services/movie.service";
import {Movie} from "../../_models/movie";
import {NgForOf} from "@angular/common";
declare var bootstrap: any;

@Component({
  selector: 'app-game-home',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './game-home.component.html',
  styleUrl: './game-home.component.css'
})
export class GameHomeComponent implements AfterViewInit {

  movies: Movie[] = [];

  constructor(private movieService: MovieService) {
    this.movieService.getMovies()
      .subscribe({
        next: (res: any) => {
          this.movies = res;
        }
      })
  }

  ngAfterViewInit(): void {
    const toastElement = document.querySelector('.toast');
    const toast = new bootstrap.Toast(toastElement);
    toast.show();
  }

}
