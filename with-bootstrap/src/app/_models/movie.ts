import {MovieCategory} from "./movie-category";
import {Director} from "./director";
import {Star} from "./star";
import {Writer} from "./writer";

export class Movie {
  id!: number;
  name!: string;
  storyLine!: string;
  releaseDate!: string;
  imageUrl!: string;
  categories!: MovieCategory[];
  directors!: Director[];
  stars!: Star[];
  writers!: Writer[];
}
