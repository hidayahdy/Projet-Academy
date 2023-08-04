import {
  BreakpointObserver,
  BreakpointState,
  MediaMatcher,
} from '@angular/cdk/layout';
import { FlatTreeControl } from '@angular/cdk/tree';
import { Component, OnInit } from '@angular/core';
import {
  MatTreeFlatDataSource,
  MatTreeFlattener,
} from '@angular/material/tree';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter, map, mergeMap, Subscription, tap } from 'rxjs';
import { Cursus } from 'src/app/models/cursus.model';
import { CursusAdminService } from 'src/app/services/cursus-admin.service';

export class Module {
  name!: string;
  url!: string;
  children?: Module[] = [];
}

interface ExampleFlatNode {
  expandable: boolean;
  name: string;
  level: number;
  url: string;
}
@Component({
  selector: 'app-sidenav-student',
  templateUrl: './sidenav-student.component.html',
  styleUrls: ['./sidenav-student.component.scss'],
})
export class SidenavStudentComponent implements OnInit {
  MODULE_TREE: Module[] = [];
  mobileQuery: MediaQueryList;
  cursus!: Cursus;
  id!: number;
  private _transformer = (node: Module, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      name: node.name,
      level: level,
      url: node.url,
    };
  };

  treeControl = new FlatTreeControl<ExampleFlatNode>(
    (node) => node.level,
    (node) => node.expandable
  );

  treeFlattener = new MatTreeFlattener(
    this._transformer,
    (node) => node.level,
    (node) => node.expandable,
    (node) => node.children
  );

  dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

  constructor(
    private cursusService: CursusAdminService,
    media: MediaMatcher,
    private breakpointObserver: BreakpointObserver,
    private activatedRoute: ActivatedRoute
  ) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this.dataSource.data = this.MODULE_TREE;
  }

  hasChild = (_: number, node: ExampleFlatNode) => node.expandable;

  ngOnInit(): void {
    const cursusId = +this.activatedRoute.snapshot.params['id'];
    this.breakpointObserver
      .observe(['(max-width: 959px)'])
      .subscribe((state: BreakpointState) => {
        this.cursusService.getCursusById(cursusId).subscribe((res) => {
          this.MODULE_TREE = [];
          if (res.moduleDto != null && res.moduleDto.length > 0) {
            res.moduleDto.forEach((submod) => {
              let newSubModule = new Module();
              newSubModule.name = submod.name;

              if (submod.lessonDto != null && submod.lessonDto.length > 0) {
                submod.lessonDto.forEach((lesson) => {
                  let newLesson = new Module();
                  newLesson.name = lesson.name;
                  this.id = lesson.id;
                  newLesson.url = `apprenti/cursus/${cursusId}/lesson/${this.id}`;
                  newSubModule.children?.push(newLesson);
                });
              }
              this.MODULE_TREE.push(newSubModule);
            });
          }

          this.dataSource.data = this.MODULE_TREE;
          console.log(this.MODULE_TREE);
        });
      });
  }
}
