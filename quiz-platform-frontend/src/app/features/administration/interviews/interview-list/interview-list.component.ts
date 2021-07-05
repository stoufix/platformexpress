import { Component, OnInit } from '@angular/core';
import { PageClient } from '@models/page-client';
import { GenericService } from '@services/generic.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-interview-list',
  templateUrl: './interview-list.component.html',
  styleUrls: ['./interview-list.component.css']
})
export class InterviewListComponent implements OnInit {

  // List of interviews
  listInterviews: any;

  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;
  searchInput: String = '';

  constructor(private genericService: GenericService, private router: Router, private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.listInterviews = [];
    this.reloadData();
  }


  /**Reload data after every action */
  reloadData() {
    this.listInterviews = this.getInterviews();
  }

  /** Get all Interviews*/
  getInterviews() {
    this.genericService.getGenericPage('/interviews', this.selectedPage, this.item)
      .subscribe(
        data => {
          this.pageClient = data;
          this.total = this.pageClient.totalElements;
          this.listInterviews = this.pageClient.content;
        });
  }

  /** Pagination: Change number of elements in the table */
  onSelect(pageNumber: number) {
    this.selectedPage = pageNumber - 1;
    if (this.searchInput !== '') {
      this.onSearch(this.searchInput);
    } else {
      this.getInterviews();
    }
  }

  /** Pagination: Change page number */
  getItems(itemsNumber: number) {
    this.item = itemsNumber;
    this.onSelect(1);
  }

  /** Search Interview */
  onSearch(search: String) {
    if (this.searchInput !== '') {
      this.genericService.searchGeneric('/interviews/search', search, this.selectedPage, this.item)
        .subscribe(
          data => {
            if (this.selectedPage > (data.totalPages) - 1) {
              this.onSelect((data.totalPages));
            } else {
              this.pageClient = new PageClient();
              this.pageClient = data;
              this.total = this.pageClient.totalElements;
              this.listInterviews = this.pageClient.content;
            }

          });
    } else {
      this.getInterviews();
    }
  }

  openInterviewDetail(id: number) {
    this.router.navigate(['detail', id], { relativeTo: this.activatedRoute });
  }

}
