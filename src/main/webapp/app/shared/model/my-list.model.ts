import { IMovie } from 'app/shared/model/movie.model';
import { INetflixUser } from 'app/shared/model/netflix-user.model';

export interface IMyList {
  id?: number;
  userID?: number;
  movies?: IMovie[];
  netflixUser?: INetflixUser;
}

export class MyList implements IMyList {
  constructor(public id?: number, public userID?: number, public movies?: IMovie[], public netflixUser?: INetflixUser) {}
}
