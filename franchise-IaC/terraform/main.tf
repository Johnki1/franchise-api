resource "aws_key_pair" "franchise_key" {
  key_name   = "franchise-key"
  public_key = file("~/.ssh/franchise-key.pub")
}

module "networking" {
  source               = "./modules/Networking"
  vpc_cidr             = var.vpc_cidr
  public_subnet_cidr   = var.public_subnet_cidr
  private_subnet_cidr  = var.private_subnet_cidr
  my_office_ip         = var.my_office_ip
}

module "ec2" {
  source            = "./modules/EC2"
  key_name          = aws_key_pair.franchise_key.key_name
  public_subnet_id  = module.networking.public_subnet_ids[0]
  security_group_id = module.networking.sg_web_id
}