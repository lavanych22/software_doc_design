import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEpisode, Episode } from 'app/shared/model/episode.model';
import { EpisodeService } from './episode.service';
import { IMovie } from 'app/shared/model/movie.model';
import { MovieService } from 'app/entities/movie/movie.service';

@Component({
  selector: 'jhi-episode-update',
  templateUrl: './episode-update.component.html'
})
export class EpisodeUpdateComponent implements OnInit {
  isSaving = false;
  movies: IMovie[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    description: [null, [Validators.required, Validators.maxLength(1000)]],
    producer: [null, [Validators.required, Validators.maxLength(100)]],
    releaseDate: [],
    videoURL: [null, [Validators.required, Validators.maxLength(1000)]],
    movie: []
  });

  constructor(
    protected episodeService: EpisodeService,
    protected movieService: MovieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ episode }) => {
      if (!episode.id) {
        const today = moment().startOf('day');
        episode.releaseDate = today;
      }

      this.updateForm(episode);

      this.movieService.query().subscribe((res: HttpResponse<IMovie[]>) => (this.movies = res.body || []));
    });
  }

  updateForm(episode: IEpisode): void {
    this.editForm.patchValue({
      id: episode.id,
      name: episode.name,
      description: episode.description,
      producer: episode.producer,
      releaseDate: episode.releaseDate ? episode.releaseDate.format(DATE_TIME_FORMAT) : null,
      videoURL: episode.videoURL,
      movie: episode.movie
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const episode = this.createFromForm();
    if (episode.id !== undefined) {
      this.subscribeToSaveResponse(this.episodeService.update(episode));
    } else {
      this.subscribeToSaveResponse(this.episodeService.create(episode));
    }
  }

  private createFromForm(): IEpisode {
    return {
      ...new Episode(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      producer: this.editForm.get(['producer'])!.value,
      releaseDate: this.editForm.get(['releaseDate'])!.value
        ? moment(this.editForm.get(['releaseDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      videoURL: this.editForm.get(['videoURL'])!.value,
      movie: this.editForm.get(['movie'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEpisode>>): void {
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

  trackById(index: number, item: IMovie): any {
    return item.id;
  }
}
