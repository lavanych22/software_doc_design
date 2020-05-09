import { Moment } from 'moment';
import { IMyList } from 'app/shared/model/my-list.model';
import { ISavedSearch } from 'app/shared/model/saved-search.model';
import { Category } from 'app/shared/model/enumerations/category.model';

export interface INetflixUser {
  id?: number;
  name?: string;
  bio?: string;
  password?: string;
  birthDate?: Moment;
  category?: Category;
  myList?: IMyList;
  savedSearches?: ISavedSearch[];
}

export class NetflixUser implements INetflixUser {
  constructor(
    public id?: number,
    public name?: string,
    public bio?: string,
    public password?: string,
    public birthDate?: Moment,
    public category?: Category,
    public myList?: IMyList,
    public savedSearches?: ISavedSearch[]
  ) {}
}
