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
import { AuthenticationService } from 'src/app/services/authentication.service';
interface FoodNode {
  name: string;
  children?: FoodNode[];
}

const TREE_DATA_ADMIN: FoodNode[] = [
  {
    name: 'Menu',
    children: [
      { name: 'Dashboard' },
      { name: 'Cursus' },
      { name: 'Candidature' },
    ],
  },
];

const TREE_DATA_STUDENT: FoodNode[] = [
  {
    name: 'Menu',
    children: [{ name: 'Dashboard' }, { name: 'Mes Cours' }],
  },
];

/** Flat node with expandable and level information */
interface ExampleFlatNode {
  expandable: boolean;
  name: string;
  level: number;
}
@Component({
  selector: 'app-tree-mobile',
  templateUrl: './tree-mobile.component.html',
  styleUrls: ['./tree-mobile.component.scss'],
})
export class TreeMobileComponent implements OnInit {
  isLoggedIn = false;
  role!: string;
  mobileQuery: MediaQueryList;
  private _transformer = (node: FoodNode, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      name: node.name,
      level: level,
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
    private authenticationService: AuthenticationService,
    media: MediaMatcher,
    private breakpointObserver: BreakpointObserver
  ) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    if (this.role === 'ROLE_ADMIN') this.dataSource.data = TREE_DATA_ADMIN;
    else this.dataSource.data = TREE_DATA_STUDENT;
  }
  ngOnInit(): void {
    this.isLoggedIn = !!this.authenticationService.getUser();
    this.role = this.authenticationService.getRoles();
    this.breakpointObserver
      .observe(['(max-width: 959px)'])
      .subscribe((state: BreakpointState) => {
        if (state.matches) {
          this.dataSource.data = this.dataSource.data;
        } else {
          this.dataSource.data = [];
        }
      });
  }

  hasChild = (_: number, node: ExampleFlatNode) => node.expandable;
}
