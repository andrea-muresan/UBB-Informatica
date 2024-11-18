export interface RelicProps {
    id?: string;
    name: string;
    location: string;
    dateInStock: Date;
    isCursed: boolean;
    price : number;
    dirty?: boolean;
}
