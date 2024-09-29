class Money{ 

        private double playerMoney;

        public Money(double playerMoney){
            this.playerMoney = playerMoney;
        }

        public double getPlayerMoney(){
            return playerMoney;
        }

        public void get(double amountToGet){
            addMoney(amountToGet);
        }

        public void spend(double amountToSpend){
            removeMoney(amountToSpend);
        }

        public void addMoney(double amountToAdd){
            playerMoney = playerMoney + amountToAdd;
        }

        public void removeMoney(double amountToRemove){
            if(getPlayerMoney() - amountToRemove < 0 || amountToRemove <= 0){
            }
            playerMoney = playerMoney - amountToRemove;
        }

    }
