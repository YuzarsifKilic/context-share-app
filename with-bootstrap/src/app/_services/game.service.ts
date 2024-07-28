import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor(private http: HttpClient) { }

  getGames(page: number | undefined, size: number | undefined) {
    if (!page && !size) {
      return this.http.get<any>(`http://localhost:8080/api/v1/games`);
    }
    return this.http.get<any>(`http://localhost:8080/api/v1/games?page=${page}&size=${size}`);
  }

  getGameById(id: number) {
    return this.http.get<any>(`http://localhost:8080/api/v1/games/${id}`);
  }
}
