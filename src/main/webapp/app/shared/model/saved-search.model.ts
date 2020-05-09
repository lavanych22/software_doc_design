import { INetflixUser } from 'app/shared/model/netflix-user.model';

export interface ISavedSearch {
  id?: number;
  userID?: number;
  search?: string;
  netflixUser?: INetflixUser;
}

export class SavedSearch implements ISavedSearch {
  constructor(public id?: number, public userID?: number, public search?: string, public netflixUser?: INetflixUser) {}
}
