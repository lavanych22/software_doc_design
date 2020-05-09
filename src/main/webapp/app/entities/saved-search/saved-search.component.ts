import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISavedSearch } from 'app/shared/model/saved-search.model';
import { SavedSearchService } from './saved-search.service';
import { SavedSearchDeleteDialogComponent } from './saved-search-delete-dialog.component';

@Component({
  selector: 'jhi-saved-search',
  templateUrl: './saved-search.component.html'
})
export class SavedSearchComponent implements OnInit, OnDestroy {
  savedSearches?: ISavedSearch[];
  eventSubscriber?: Subscription;

  constructor(
    protected savedSearchService: SavedSearchService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.savedSearchService.query().subscribe((res: HttpResponse<ISavedSearch[]>) => (this.savedSearches = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSavedSearches();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISavedSearch): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSavedSearches(): void {
    this.eventSubscriber = this.eventManager.subscribe('savedSearchListModification', () => this.loadAll());
  }

  delete(savedSearch: ISavedSearch): void {
    const modalRef = this.modalService.open(SavedSearchDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.savedSearch = savedSearch;
  }
}
