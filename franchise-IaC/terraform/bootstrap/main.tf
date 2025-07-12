resource "aws_s3_bucket" "tfstate" {
  bucket = "franchise-terraform-state-johnki-20250712"
  tags = {
    Name = "Franchise-TFState"
  }
}

resource "aws_s3_bucket_versioning" "tfstate_versioning" {
  bucket = aws_s3_bucket.tfstate.id

  versioning_configuration {
    status = "Enabled"
  }
}

resource "aws_dynamodb_table" "tf_locks" {
  name         = "franchise-terraform-locks-johnki-20250712"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "LockID"

  attribute {
    name = "LockID"
    type = "S"
  }

  tags = {
    Name = "Franchise-TFLocks"
  }
}

