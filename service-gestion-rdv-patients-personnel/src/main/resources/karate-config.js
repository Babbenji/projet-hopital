function fn(){
    var env= karate.env;
    if (!env){
        env = 'dev';

    }
    var config = {
        env: env,
        myVarName: 'hello karate',
        tokenID:'eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZnJlZEBob3BpdGFsLXNlY3JldGFpcmUuZnIiLCJleHAiOjE2ODA0ODkxMDcsImlhdCI6MTY4MDQ1MzEwNywic2NvcGUiOiJTRUNSRVRBSVJFIn0.I6HEMhVI1ZU_Mil7uRYds5VWT5pSdXXn6oNhDZhsnTniY-Uera3BYo2v4012KhALSJ9lmtc4St6HTbEJ8RjEcLYbLBIuhly-rufRxrtHbWexX6NqqVDpzHULB-uJg084ZDVcxZdV7aoLWT862BnLIDUdJFG9MF7WuJe3mZDOJWHtR38Vw5rAEYuvZRDezY1KcCgeW3PeFY-MjQDzmgxObNdfktdKYwddhhl2tpgCwem8xqlY5jwddU2AoA28ET_pdT24RHjtOMOwb28auSlYWCA-Eq9ESOYLviu0XLhYf7JUoSzR9z1yDgqc8TJnIRznn46UMJ3KktABswU_l1DAGA'
    }
}