terraform {
  backend "s3" {
    bucket         = "franchise-terraform-state-johnki-20250712"
    key            = "franchise-api/terraform/terraform.tfstate"
    region         = "us-east-1"
    dynamodb_table = "franchise-terraform-locks-johnki-20250712"
    profile        = "franchise-deployer"
  }
}
