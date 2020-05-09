import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMovie, Movie } from 'app/shared/model/movie.model';
import { MovieService } from './movie.service';
import { IMyList } from 'app/shared/model/my-list.model';
import { MyListService } from 'app/entities/my-list/my-list.service';

@Component({
  selector: 'jhi-movie-update',
  templateUrl: './movie-update.component.html'
})
export class MovieUpdateComponent implements OnInit {
  isSaving = false;
  mylists: IMyList[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    description: [null, [Validators.required, Validators.maxLength(1000)]],
    releaseDate: [],
    type: [],
    genre: [],
    creator: [null, [Validators.required, Validators.maxLength(100)]],
    cast: [null, [Validators.required, Validators.maxLength(200)]],
    rating: [],
    link: [null, [Validators.required, Validators.maxLength(1000)]],
    availableInHD: [],
    myList: []
  });

  constructor(
    protected movieService: MovieService,
    protected myListService: MyListService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ movie }) => {
      if (!movie.id) {
        const today = moment().startOf('day');
        movie.releaseDate = today;
      }

      this.updateForm(movie);

      this.myListService.query().subscribe((res: HttpResponse<IMyList[]>) => (this.mylists = res.body || []));
    });
  }

  updateForm(movie: IMovie): void {
    this.editForm.patchValue({
      id: movie.id,
      name: movie.name,
      description: movie.description,
      releaseDate: movie.releaseDate ? movie.releaseDate.format(DATE_TIME_FORMAT) : null,
      type: movie.type,
      genre: movie.genre,
      creator: movie.creator,
      cast: movie.cast,
      rating: movie.rating,
      link: movie.link,
      availableInHD: movie.availableInHD,
      myList: movie.myList
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const movie = this.createFromForm();
    if (movie.id !== undefined) {
      this.subscribeToSaveResponse(this.movieService.update(movie));
    } else {
      this.subscribeToSaveResponse(this.movieService.create(movie));
    }
  }

  private createFromForm(): IMovie {
    return {
      ...new Movie(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      releaseDate: this.editForm.get(['releaseDate'])!.value
        ? moment(this.editForm.get(['releaseDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      type: this.editForm.get(['type'])!.value,
      genre: this.editForm.get(['genre'])!.value,
      creator: this.editForm.get(['creator'])!.value,
      cast: this.editForm.get(['cast'])!.value,
      rating: this.editForm.get(['rating'])!.value,
      link: this.editForm.get(['link'])!.value,
      availableInHD: this.editForm.get(['availableInHD'])!.value,
      myList: this.editForm.get(['myList'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovie>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IMyList): any {
    return item.id;
  }
}
